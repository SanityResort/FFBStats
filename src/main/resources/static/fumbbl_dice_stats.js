// ==UserScript==
// @name         FUMBBL Dice Stats
// @namespace    http://ffbstats.butterbrot.org/
// @version      0.1
// @author       Candlejack
// @match        https://fumbbl.com/FUMBBL.php?page=match&id=*
// @require      http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js
// ==/UserScript==



$(document).ready(function() {
    var linkTag =  $("a[href*='/ffblive.jnlp?replay=860124']");
    var link = $("a[href*='/ffblive.jnlp?replay=860124']").attr('href');
    var id = link.split('=')[1];
    var parent = $(linkTag).parent();
    parent.html(parent.html() + '<br><a href="https://ffbstats.herokuapp.com/stats/'+id+'" target="_blank">Dice Stats</a>');

});