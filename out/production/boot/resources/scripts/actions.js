var actions = (function() {
 function processLoad() {
   $('#actions button').click(buttonClick);
   $('.tile, .node, .connector').click(boardClick);
 }

 function boardClick(event) {
   var id = event.toElement.id;
   var tile = parseInt(id.substring(1, 3));

   var data = { tile: tile };
   if (id.length > 2) {
       if (id[3] == 'n') {
           data.node = parseInt(id[4]);
       } else if (id[3] == 'c') {
           data.connector = parseInt(id[4]);
       }
   }
   sendAction(data);
 }

 function buttonClick(event) {
   var data = { action: $(this).attr('action') };
   sendAction(data);
 }

 function sendAction(data) {
   console.log(data);
   $.ajax({
       method: 'POST',
       url: '/v1/games/' + GAME_ID + '/players/' + PLAYER_ID + '/action',
       contentType: 'application/json',
       data: JSON.stringify(data)
     })
     .fail(handleError)
     .always(function(data) {
       setTimeout(events.load, 500);
     });
 }

 return {
   load: function() {
     processLoad();
   }
  };
})();