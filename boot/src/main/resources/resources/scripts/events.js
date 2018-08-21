var events = (function() {
 var VERSION = 0;
 var IN_PROGRESS = false;
 var RELOAD = false;
 var HELP = {
   SETTLEMENT: 'To build a settlement, click on an available unoccupied settlement: o',
   ROAD: 'To build a road, click on an available unoccupied road: /, \\, |',
   ROLL: 'To roll the dice, click on the Roll Dice button',
   PLACE_ROBBER: 'To place the robber, click on a tile',
   STEAL_RESOURCE: 'Click on an adjacent player\'s settlement to steal a random resource',
   BUILD: 'Build or Use a Development card, when finished click on the End Turn button'
 };
 var EVENTS = {
   SETUP_FIRST_SETTLEMENT: { text : 'build their first settlement', help: HELP.SETTLEMENT },
   SETUP_FIRST_ROAD: { text : 'build their first road', help: HELP.ROAD },
   SETUP_SECOND_SETTLEMENT: { text : 'place their second settlement', help: HELP.SETTLEMENT },
   SETUP_SECOND_ROAD: { text : 'construct their second road', help: HELP.ROAD },
   ROLL: { text : 'roll the dice', help: HELP.ROLL },
   PLACE_ROBBER: { text : 'place the robber', help: HELP.PLACE_ROBBER },
   STEAL_RESOURCE: { text : 'steal a resource', help: HELP.STEAL_RESOURCE},
   BUILD: { text : 'finish their build phase', help: HELP.BUILD },
   BUILD_ROAD: { text : 'build a road', help: HELP.ROAD },
   BUILD_SETTLEMENT: { text : 'construct a settlement', help: HELP.SETTLEMENT }
 };

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
    $('#currentEvent').html(format('Waiting for @p' + result.meta.player + ' to ' + EVENTS[result.meta.stage].text +
      '. <span id="help" title="' + EVENTS[result.meta.stage].help + '">(?)</span>'));
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