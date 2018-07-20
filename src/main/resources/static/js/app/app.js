'use strict'

var trackApp = angular.module('tracks', [ 'ui.bootstrap', 'track.controllers',
		'track.services' ]);
trackApp.constant("CONSTANTS", {
	getTrackByIdUrl : "/track/getTrack/",
	getAllTracks : "/track/getAllTracks",
	saveTrack : "/track/saveTrack"
});