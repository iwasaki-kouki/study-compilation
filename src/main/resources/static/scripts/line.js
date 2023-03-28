const elem =document.querySelector("#a");
elem.innerText='java';
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
        
// 線の描写
let line = (fig,x1,y1,x2,y2,thick,color) => {
      fig.beginPath();            // 新しいパスを作成
      fig.lineWidth = thick;      // 線の太さ
      fig.strokeStyle = color;    // 線の色
      fig.moveTo(x1,y1);          // 線の開始座標
      fig.lineTo(x2,y2);          // 線の終了座標
      fig.stroke();               // 輪郭を描画
    };
    


// 開始関数
let strDraw = () => {
  // ドキュメントオブジェクト作成
  let id1 = document.getElementById('pos');

  // 2Dオブジェクト作成
  let fig = id1.getContext('2d');

  // 直線描画
  line(fig,25, 25,175, 25,1,"black");
  line(fig,25, 75,175, 75,2,"red");
  line(fig,25,125,175,125,4,"green");
  line(fig,25,175,175,175,8,"blue");
};
strDraw();