var events = (function() {
 var VERSION = 0;
 var IN_PROGRESS = false;
 var RELOAD = false;
 var EVENTS = {
   SETUP: 'setup the game',
   SETUP_FIRST_SETTLEMENT: 'build their first settlement',
   SETUP_FIRST_ROAD: 'build their first road',
   SETUP_SECOND_SETTLEMENT: 'place their second settlement',
   SETUP_SECOND_ROAD: 'construct their second road',
   ROLL: 'roll the dice',
   PLACE_ROBBER: 'place the robber'
 }

 function safeProcessLoad() {
  if (IN_PROGRESS) {
   RELOAD = true;
  } else {
   processLoad();
  }
 }

 function processLoad() {
   IN_PROGRESS = true;
   $('#currentEvent').text('Loading...');
   $.ajax('/v1/games/' + GAME_ID + '/players/' + PLAYER_ID + '/events?version=' + (VERSION - 1))
     .done(function(result) {
       processEvent(result, 0);
     }).fail(handleError);
 }

 function processEvent(result, i) {
   if (i < result.data.length) {
       var event = result.data[i];
       $('#eventList').prepend('<div>' + format(event.message) + '.</div>');
       switch (event.building) {
        case 'SETTLEMENT':
           $('#t' + pad(event.tileId, 2) + 'n' + event.position).addClass('player' + event.playerId).text('x');
           break;
        case 'ROAD':
           $('#t' + pad(event.tileId, 2) + 'c' + event.position).addClass('player' + event.playerId);
           break;
        case 'ROBBER':
           tiles.robber(event.tileId);
           break;
        default:
           if (event.resources) {
             resources.add(event.playerId, event.resources);
           }
           if (event.otherResources) {
             resources.add(event.otherPlayerId, event.otherResources);
           }
           break;
       }
       players.select(event.playerId);
       setTimeout(function() { processEvent(result, i+1) }, 2000);
   } else {
    VERSION = result.meta.version;
    $('#currentEvent').html(format('Waiting for @p' + result.meta.player + ' to ' + EVENTS[result.meta.stage] + '.'));
    players.select(result.meta.player);
    IN_PROGRESS = false;
    if (RELOAD) {
     RELOAD = false;
     processLoad();
    }
   }
 }

 return {
   load: safeProcessLoad
  };
})();