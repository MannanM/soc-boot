var GAME_ID = 100;
var PLAYER_ID = 200;

$(function() {
    initialise();
});

function initialise() {
   $.ajax('/v1/games/' + GAME_ID + '/state')
     .done(function(result) {
       tiles.load(result.data.tiles);
       players.load(result.data.players);
       events.load();
       actions.load();
     }).fail(handleError);
}

function handleError(xhr, status, errorThrown) {
 var error = jQuery.parseJSON(xhr.responseText);
 alert(error.message);
};

function format(inputString) {
 var result = players.format(inputString);
 result = tiles.format(result);
 return result;
}

function pad(str, max) {
 str = str.toString();
 return str.length < max ? pad('0' + str, max) : str;
}

function titleCase(str) {
 str = str.toLowerCase().split(' ');
 for (var i = 0; i < str.length; i++) {
  str[i] = str[i].charAt(0).toUpperCase() + str[i].slice(1);
 }
 return str.join(' ');
}