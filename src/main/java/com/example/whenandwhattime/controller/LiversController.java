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
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.whenandwhattime.entity.Livers;
import com.example.whenandwhattime.entity.UserInf;
import com.example.whenandwhattime.form.LiverForm;
import com.example.whenandwhattime.form.EditForm;
import com.example.whenandwhattime.repository.LiversRepository;
import com.example.whenandwhattime.repository.YoutubeRepository;
import com.example.whenandwhattime.entity.Favorites;
import com.example.whenandwhattime.form.FavoriteForm;

@Controller
public class LiversController {
    protected static Logger log = LoggerFactory.getLogger(LiversController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LiversRepository repository;
    
    @Autowired
    private YoutubeRepository yourepository;

    @Autowired
    private HttpServletRequest request;
    
    @Value("${image.local:false}")
    private String imageLocal;
    
    /*ライバー一覧表示*/
    @GetMapping("/livers")
    public String index(Principal principal,Model model) throws IOException {
    	Authentication authentication =(Authentication) principal;
    	UserInf user = null;
    	if(authentication!=null) {
    	user =(UserInf) authentication.getPrincipal();
    	}
    	model.addAttribute("list", list(user));
    	model.addAttribute("user", user);
    	
    	
        return "liver/index";
    }
    
    /*管理画面での一覧表示*/
    @GetMapping(path="/adminlivers")
    public String adminindex(Model model) throws IOException {

    	model.addAttribute("list", list(null));
    	
        return "admin/liver";
    }
    
    /*検索*/
    @PostMapping(path = "/search")
    String search(@RequestParam String name,Model model,Principal principal) throws IOException {
    	if(name.equals("")) {
      		index(principal,model);
      	}else {
    		Iterable<Livers> livers=repository.findByNameContaining(name);
        	List<LiverForm> list = new ArrayList<>();
        	for (Livers entity : livers) {
                LiverForm form = getLivers(null,entity);
                list.add(form);
        	};
        	model.addAttribute("list", list);
      	}
    	return "liver/index";
    }
    
    
    /*まとめる部分*/
    private List<LiverForm> list(UserInf user) throws IOException{
    	Iterable<Livers> livers = repository. findAllByOrderByIdAsc();
    	List<LiverForm> list = new ArrayList<>();
    	for (Livers entity : livers) {
            LiverForm form = getLivers(user,entity);
            list.add(form);
    	};
    	
		return list;
    }
    

    
    
    
    /*編集画面*/
    @PostMapping(path = "/edit", params = "edit")
    String edit(@RequestParam long id,Model model) throws IOException {
    	Livers liver= repository.findById(id).orElse(new Livers());
    	LiverForm edit =getLivers(null,liver);
    	model.addAttribute("liver", edit);
        return "admin/edit";
    }
    
    @PostMapping(path = "/edit", params = "regist")
    String regist(@RequestParam long id,@Validated @ModelAttribute("liver") EditForm form,BindingResult result,
            Model model, RedirectAttributes redirAttrs,@RequestParam MultipartFile image) throws IOException{
        if (result.hasErrors()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", "投稿に失敗しました。");
            return "redirect:/adminlivers";
        }
        boolean isImageLocal = false;
        
        if (imageLocal != null) {
            isImageLocal = new Boolean(imageLocal);
        }
        
        
        
    	Optional<Livers> liver= repository.findById(id);
    	Livers entity =liver.get();
        File destFile = null;
        if (image.isEmpty()) {
        }else {
            if (isImageLocal) {
                destFile = saveImageLocal(image, entity);
                entity.setThumbnail(destFile.getAbsolutePath());
            } else {
                entity.setThumbnail("");
            };
        }
        
        
        entity.setName(form.getName());
        entity.setTwitter_url(form.getTwitter_URL());
        entity.setYoutube_url(form.getYoutube_URL());
        entity.setLanguage(form.getLanguage());
		repository.save(entity);

    	return "redirect:/adminlivers";
    }
    
    

    @PostMapping(path = "/edit", params = "back")
    String back() {
        return "redirect:/adminlivers";
    }
    
    @Transactional
    @PostMapping(path = "/edit", params = "delete")
    public String delete(@RequestParam long id) {
    	yourepository.deleteALLByLivers_id(id);
    	repository.deleteById(id);
        return "redirect:/adminlivers";
    }
    
    
    
    public LiverForm getLivers(UserInf user,Livers entity) throws FileNotFoundException, IOException {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(Livers.class, LiverForm.class).addMappings(mapper -> mapper.skip(LiverForm::setFavorites));
        modelMapper.typeMap(Favorites.class, FavoriteForm.class).addMappings(mapper -> mapper.skip(FavoriteForm::setLiver));
        
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
        
        if(user!=null) {
	        List<FavoriteForm> favorites = new ArrayList<FavoriteForm>();
	        for (Favorites favoriteEntity : entity.getFavorites()) {
	        	           FavoriteForm favorite = modelMapper.map(favoriteEntity, FavoriteForm.class);
	        	           favorites.add(favorite);
	        	           if (user.getUserId().equals(favoriteEntity.getUserId())) {
	        	        	                 form.setFavorite(favorite);
	        	              }
	        	           
	        }
	        form.setFavorites(favorites);
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

    @PostMapping("/liverform")
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
        entity.setLanguage(form.getLanguage());
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