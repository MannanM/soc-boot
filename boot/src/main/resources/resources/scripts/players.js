var players = (function() {
 var PLAYERS = [];
 var YOU = -2;

 function processLoad(playerData) {
   for (var i = 0; i < playerData.length; i++) {
     var player = playerData[i];
     PLAYERS.push(player);
     $('#playerList').append('<div id="p' + i + '" class="player' + i +'"><span class="pointer"></span>'
       + player.name + '<div class="resources"></div></div>');
     if (player.id == PLAYER_ID) {
      YOU = i;
      ['TREE', 'SHEEP', 'MUD', 'IRON', 'WHEAT'].forEach(function(resource) {
       $('#p' + i + ' div.resources').append('<span class="' + resource.toLowerCase() +
         '">' + resource + ': <strong>0</strong></span>');
      });
     } else {
       $('#p' + i + ' div.resources').append('<span class="resource">Resources: <strong>0</strong></span>');
     }
   }
 }

 function processSelect(playerId) {
  PLAYERS.forEach(function(player, index) {
   var text = index == playerId ? '-> ' : '';
   $('#p' + index + ' span.pointer').text(text);
  });
 }

 function processIsPlayer(playerId) {
  return YOU == playerId;
 }

 function processGetName(playerId) {
  return PLAYERS[playerId].name;
 }

 function processFormat(inputString) {
  var result = inputString;
  PLAYERS.forEach(function(player, index) {
   result = result.replace(new RegExp('@p' + index, 'g'), '<span class="player' + index + '">' + player.name + "</span>");
  });
  return result;
 }

 return {
   load: processLoad,
   select: processSelect,
   isPlayer: processIsPlayer,
   getName: processGetName,
   format: processFormat
  };
})();