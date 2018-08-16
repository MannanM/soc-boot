var resources = (function() {
 function processAdd(player, resources) {
   Object.keys(resources).forEach(function(key, index) {
    var id = '#p' + player + ' .resource strong';
    if (players.isPlayer(player)) {
      var id = '#p' + player + ' .' + key.toLowerCase() + ' strong';
    }
    var currentValue = parseInt($(id).text());
    $(id).text(currentValue + resources[key]);
   });
 }

 return {
   add: function(player, resources) {
    processAdd(player, resources);
   }
  };
})();