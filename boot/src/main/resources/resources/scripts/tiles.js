var tiles = (function() {
 var TILES = ['TREE', 'SHEEP', 'MUD', 'IRON', 'WHEAT', 'DESERT', 'RESOURCE']

 function processLoad(tileData) {
  var initialRobber = -1;
  tileData.forEach(function(tile, i) {
   var ident = '#t' + pad(i, 2);
   $(ident + ',' + ident + 'top,' + ident + 'bottom')
     .addClass(tile.type.toLowerCase())
     .attr('title', titleCase(tile.type));

   initialRobber = addNumber(tile.number, i, initialRobber);
  });
  processRobber(initialRobber);
 }

 function processRobber(tileId) {
   for (var i = 0; i <= 18; i++) {
    var ident = '#t' + pad(i, 2);
    var tile = $(ident);
    if (i !== tileId) {
     $(ident + ',' + ident + 'top,' + ident + 'bottom').removeClass('robber');
     tile.text(tile.attr('number'));
    } else {
     $(ident + ',' + ident + 'top,' + ident + 'bottom').addClass('robber');
     tile.html('o<br />( )<br />--');
    }
   }
 }

 function processFormat(inputString) {
  var result = inputString;
  TILES.forEach(function(tile) {
   result = result.replace(new RegExp('#' + tile, 'g'),
     '<span class="' + tile.toLowerCase() + '">' + titleCase(tile) + "</span>");
  });
  return result;
 }

 function addNumber(number, i, initialRobber) {
   if (number == 7) {
    $('#t' + pad(i, 2)).text('').attr('number', '');
    return i;
   }
   $('#t' + pad(i, 2)).text(number).attr('number', number);
   return initialRobber;
 }

 return {
   load: function(tileData) {
    processLoad(tileData);
   },
   robber: function(tileId) {
    processRobber(tileId);
   },
   format: function(inputString) {
    return processFormat(inputString);
   }
  };
})();