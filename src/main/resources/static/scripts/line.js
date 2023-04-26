let date = new Date();
// 時計
        let myfunc=()=>{
			let date = new Date();
            let hours = date.getHours();
            let minutes = date.getMinutes();
            let seconds = date.getSeconds();
            nowtime.innerHTML = hours+"時"+minutes+"分"+seconds+"秒" ;
        }
 
        setInterval("myfunc()", 500);

// スクロールさせる        
timeline.scrollTop = 0;
timeline.scrollTop = date.getHours()*360;


// モーダルウィンドウを作る
window.onload = function(){
	// 変数に要素を入れる
	var open = $('.modal-open'),
		close = $('.modal-close'),
		container = $('.modal-container');

	//開くボタンをクリックしたらモーダルを表示する
	open.on('click',function(){	
		container.addClass('active');
		return false;
	});

	//閉じるボタンをクリックしたらモーダルを閉じる
	close.on('click',function(){	
		container.removeClass('active');
	});

	//モーダルの外側をクリックしたらモーダルを閉じる
	$(document).on('click',function(e) {
		if(!$(e.target).closest('.modal-body').length) {
			container.removeClass('active');
		}
	});
}