function mySwitch1(){
    document.getElementById('1').style.display = document.getElementById('1').style.display=='none'?'block':'none';
}
function mySwitch2(){
    document.getElementById('2').style.display = document.getElementById('2').style.display=='none'?'block':'none';
}
function changeBody(index){
  switch(index){
    case 1:{
      document.getElementById('1').style.display = "";
      document.getElementById('2').style.display = "none";
      document.getElementById('3').style.display = "none";
    }
    case 2:{
      document.getElementById('1').style.display = "none";
      document.getElementById('2').style.display = "";
      document.getElementById('3').style.display = "none";
    }
    case 3:{
      document.getElementById('1').style.display = "none";
      document.getElementById('2').style.display = "none";
      document.getElementById('3').style.display = "";
    }
  }
}