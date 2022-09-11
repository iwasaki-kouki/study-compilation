package com.example.whenandwhattime.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.whenandwhattime.entity.Livers;
import com.example.whenandwhattime.entity.UserInf;
import com.example.whenandwhattime.form.LiverForm;
import com.example.whenandwhattime.form.UserForm;
import com.example.whenandwhattime.repository.LiversRepository;

@Controller
public class LiversController {
    protected static Logger log = LoggerFactory.getLogger(LiversController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LiversRepository repository;

    @Autowired
    private HttpServletRequest request;
    
    @Value("${image.local:false}")
    private String imageLocal;
    
    @GetMapping(path="/livers")
    public String index(Model model) throws IOException {
    	Iterable<Livers> livers = repository.findAll();
    	List<LiverForm> list = new ArrayList<>();
    	for (Livers entity : livers) {
            LiverForm form = getLivers(entity);
            list.add(form);
        }
    	model.addAttribute("list", list);
    	
        return "liver/index";
    }
    
    public LiverForm getLivers(Livers entity) throws FileNotFoundException, IOException {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(Livers.class, LiverForm.class).addMappings(mapper -> mapper.skip(LiverForm::setUser));

        boolean isImageLocal = false;
        if (imageLocal != null) {
            isImageLocal = new Boolean(imageLocal);
        }
        LiverForm form = modelMapper.map(entity, LiverForm.class);

        if (isImageLocal) {
            try (InputStream is = new FileInputStream(new File(entity.getThumbnail()));
                    ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                byte[] indata = new byte[10240 * 16];
                int size;
                while ((size = is.read(indata, 0, indata.length)) > 0) {
                    os.write(indata, 0, size);
                }
                StringBuilder data = new StringBuilder();
                data.append("data:");
                data.append(getMimeType(entity.getThumbnail()));
                data.append(";base64,");

                data.append(new String(Base64Utils.encode(os.toByteArray()), "ASCII"));
                form.setImageData(data.toString());
            }
        }


        return form;
    }
    
	private String getMimeType(String path) {
        String extension = FilenameUtils.getExtension(path);
        String mimeType = "image/";
        switch (extension) {
        case "jpg":
        case "jpeg":
            mimeType += "jpeg";
            break;
        case "png":
            mimeType += "png";
            break;
        case "gif":
            mimeType += "gif";
            break;
        }
        return mimeType;
    }
    
	
	
	
    @GetMapping(path = "/liver/new")
    public String newTopic(Model model) {
        model.addAttribute("form", new LiverForm());
        return "liver/liverform";
    }

    @RequestMapping(value = "/liverform", method = RequestMethod.POST)
    public String create(Principal principal, @Validated @ModelAttribute("form") LiverForm form, BindingResult result,
            Model model, @RequestParam MultipartFile image, RedirectAttributes redirAttrs)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", "投稿に失敗しました。");
            return "liver/liverform";
        }

        boolean isImageLocal = false;
        if (imageLocal != null) {
            isImageLocal = new Boolean(imageLocal);
        }

        Livers entity = new Livers();
        File destFile = null;
        if (isImageLocal) {
            destFile = saveImageLocal(image, entity);
            entity.setThumbnail(destFile.getAbsolutePath());
        } else {
            entity.setThumbnail("");
        };
        entity.setName(form.getName());
        entity.setTwitter_url(form.getTwitter_URL());
        entity.setYoutube_url(form.getYoutube_URL());
        repository.saveAndFlush(entity);

        redirAttrs.addFlashAttribute("hasMessage", true);
        redirAttrs.addFlashAttribute("class", "alert-info");
        redirAttrs.addFlashAttribute("message", "投稿に成功しました。");

        return "redirect:/livers";
    }

    private File saveImageLocal(MultipartFile image, Livers entity) throws IOException {
        File uploadDir = new File("/uploads");
        uploadDir.mkdir();

        String uploadsDir = "/uploads/";
        String realPathToUploads = request.getServletContext().getRealPath(uploadsDir);
        if (!new File(realPathToUploads).exists()) {
            new File(realPathToUploads).mkdir();
        }
        String fileName = image.getOriginalFilename();
        File destFile = new File(realPathToUploads, fileName);
        image.transferTo(destFile);

        return destFile;
    }

}