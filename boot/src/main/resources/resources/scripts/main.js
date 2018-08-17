var GAME_ID = qs('gameId');
var PLAYER_ID = qs('playerId');

$(function() {
   $.ajax('/v1/games/' + GAME_ID + '/state')
     .done(function(result) {
       tiles.load(result.data.tiles);
       players.load(result.data.players);
       actions.load();
       refresh();
     }).fail(handleError);
});

function refresh() {
  events.load();
  setTimeout(refresh, 5000);
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

function qs(key) {
 key = key.replace(/[*+?^$.\[\]{}()|\\\/]/g, "\\$&"); // escape RegEx meta chars
 var match = location.search.match(new RegExp("[?&]"+key+"=([^&]+)(&|$)"));
 return match && decodeURIComponent(match[1].replace(/\+/g, " "));
}