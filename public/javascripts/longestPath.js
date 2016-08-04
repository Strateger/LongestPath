if (window.console) {
  console.log("Welcome to the Longest Path!");
}


function getGridValuesInCommaList(){
  return jQuery(".gridCell")
    .map(function() {
       return this.value;
     }).get().join();
}

function createNewGrid(){
    cols = parseInt(jQuery("#gridCols").val());
    rows = parseInt(jQuery("#gridRows").val());
    maxInt = parseInt(jQuery("#maxNumber").val());

    screenWidth = jQuery( document ).width()-50 ;
    celSize = Math.floor(screenWidth/(cols+1));
    console.log(cols+1);
    jQuery("#TheGrid").empty();
    for(y = 0; y<rows; y++){
       for(x = 0; x<cols; x++){
          jQuery("#TheGrid").append( '<input type="text" style="width:' + celSize + 'px;  height:' + celSize + 'px; " value=' + (Math.floor(Math.random() * maxInt) + 1) + ' class="gridCell">')
       }
       jQuery("#TheGrid").append('<div style="clear:both;"></div>');
    }
}

function setGridClassSize(){
    cols = parseInt(jQuery("#gridCols").val());
    screenWidth = jQuery( document ).width()-50 ;
    celSize = Math.floor(screenWidth/(cols+1));
    jQuery('.gridCell').css('width', celSize + 'px');
    jQuery('.gridCell').css('height', celSize + 'px');
}

function animateGridCellColorChange(){
    jQuery('.gridCell').css('opacity', '.4');
    jQuery('.gridCell' ).animate({ opacity: 1}, 1000);
}

function colorLongestPath(){
  var maxInt = parseInt(jQuery("#maxNumber").val());
  var redHex =  parseInt('50', 16);
  var greenHex =  parseInt('dd', 16);
  var blueHex =  parseInt('50', 16);

  jQuery(".longestClass")
      .map(function() {
         var red = ("00" +  parseInt(redHex*((this.value )/maxInt)).toString(16)).slice(-2);
         var green = ("00" +  parseInt(greenHex*((this.value)/maxInt)).toString(16)).slice(-2);
         var blue = ("00" +  parseInt(blueHex*((this.value )/maxInt)).toString(16)).slice(-2);
         var color = "#" + red + green + blue;

         jQuery(this).css('background-color', color);
         if(Math.max( parseInt(red,16),  parseInt(green,16),  parseInt(blue,16))  < 110 ){
            jQuery(this).css('color', "white");
         }
      });
}



function findLongestPath(){

    var gridWidth = parseInt(jQuery("#gridCols").val());
    var commaListValues = (getGridValuesInCommaList());

    if(commaListValues == ""){
       alert("Create a graph first");
       return;
    }

    var commaData =  getGridValuesInCommaList();
    var r = jsRoutes.controllers.LongestPath.findLongestPath(gridWidth);

    r.ajax({
        type :  "POST",
        dataType:  'text',
        contentType: "text/plain",

        data: commaData,
        success: function(responce) {
           jQuery("#TheGrid").html(responce);
        }, error:  function(responce) {    }
    });
}

function maxNumberCheck(arg_this){
   if(arg_this.value > 255 || arg_this.value < 1){
      alert('Numbers between 1 and 255 please');
      arg_this.value = 255;
   }
}

