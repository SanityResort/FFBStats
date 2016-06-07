// ==UserScript==
// @name         FUMBBL Dice Stats
// @namespace    http://ffbstats.butterbrot.org/
// @version      0.3
// @author       Candlejack
// @match        http*://fumbbl.com/FUMBBL.php?page=match*
// @match        http*://fumbbl.com/p/match?*
// @require      http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js
// ==/UserScript==

function setLink() {
    try {
        var linkTag =  $('a[href*=\'/ffblive.jnlp?replay=\']');
        var link = $(linkTag).attr('href');
        var id = link.split('=')[1];
        var parent = $(linkTag).parent();
        parent.html(parent.html() + '<br><a href="https://ffbstats.herokuapp.com/stats/'+id+'" target="_blank">Dice Stats</a>');
    } catch (e) {
        console.log("Could not set link, retry in 2 seconds", e);
        setTimeout(setLink, 2000);
    }
}

$(document).ready(function() {
    setLink();
});