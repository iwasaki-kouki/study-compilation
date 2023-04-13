let date = new Date();
// 時計
        let myfunc=()=>{
			let date = new Date();
            let hours = date.getHours();
            let minutes = date.getMinutes();
            let seconds = date.getSeconds();
            myid.innerHTML = hours+"時"+minutes+"分"+seconds+"秒" ;
        }
 
        setInterval("myfunc()", 500);
        
// スクロールさせる        
timeline.scrollTop = 0;
timeline.scrollTop = date.getHours()*360;