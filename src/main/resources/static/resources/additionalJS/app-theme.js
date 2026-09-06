/* ============================================================================
   app-theme.js - makes every page follow the dashboard's dark/light setting.

   The dashboard owns the toggle and stores the choice in
   localStorage["dash_theme"] ("light" | "dark"). Pages load inside an iframe
   in welcome.html, so there are three ways a page learns the theme:

     1. on load          - read localStorage directly
     2. while open       - the "storage" event fires in OTHER frames when the
                           dashboard writes the key, so a page already open in
                           a background tab/frame updates live
     3. same-frame swap  - the shell posts {type:"theme"} down to the iframe

   This only sets a data-theme attribute; all the actual colours live in
   app-theme.css. No markup, ids, classes, or behaviour are touched.
   ============================================================================ */
(function () {
	"use strict";

	var KEY = "dash_theme";

	function read() {
		try { return localStorage.getItem(KEY) === "dark" ? "dark" : "light"; }
		catch (e) { return "light"; }
	}

	function apply(t) {
		var v = (t === "dark") ? "dark" : "light";
		var el = document.documentElement;
		if (el.getAttribute("data-theme") !== v) el.setAttribute("data-theme", v);
	}

	/* Run immediately, before first paint: this script is loaded in <head>, so
	   the attribute is already correct when the body renders and there is no
	   white flash on a dark theme. */
	apply(read());

	/* the dashboard wrote the key from another frame */
	window.addEventListener("storage", function (ev) {
		if (ev.key === KEY) apply(ev.newValue);
	});

	/* the shell (or the dashboard) told us directly */
	window.addEventListener("message", function (ev) {
		if (ev.origin !== window.location.origin) return;
		var d = ev.data;
		if (d && d.type === "theme") apply(d.value);
	});

	/* a page restored from bfcache can hold a stale theme */
	window.addEventListener("pageshow", function () { apply(read()); });
})();
