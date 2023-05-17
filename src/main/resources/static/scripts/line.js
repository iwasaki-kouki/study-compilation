
// 時計
 let time=()=>{
			let date = new Date();
			timeline.scrollTop = 0;
			timeline.scrollTop = date.getHours()*360;

        }
 
        setInterval("time()", 500);

// スクロールさせる        




// モーダルウィンドウを作る
window.onload = function(){
	let date = new Date();
	timeline.scrollTop = 0;
	timeline.scrollTop = date.getHours()*360;
	
	// 変数に要素を入れる
	var open = $('.modal-open'),
		close = $('.modal-close'),
		container = $('.modal-container');


	//開くボタンをクリックしたらモーダルを表示する
        open.on('click',function(){
			$('#'+$(this).data('target')).addClass('active');
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