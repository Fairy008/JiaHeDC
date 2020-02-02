define([],function(){
   var win = {
       showMessageWin : function(message){
           var body = document.getElementsByTagName('body')[0];
           var messageDiv = document.createElement('div');
           messageDiv.id = 'showMessageWin';
           messageDiv.innerText = message;
           body.appendChild(messageDiv);
           window.setTimeout(function(){
               body.removeChild(document.getElementById('showMessageWin'));
           },1500);
       }
   };

   return win;

});