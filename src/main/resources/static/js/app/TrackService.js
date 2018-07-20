'use strict'

angular.module('tracks.services', []).factory('TrackService',
		[ "$http", "CONSTANTS", function($http, CONSTANTS) {
			var service = {};
			service.getTrackById = function(trackId) {
				var url = CONSTANTS.getTrackByIdUrl + trackId;
				return $http.get(url);
			}
			service.getAllTracks = function() {
				return $http.get(CONSTANTS.getAllTracks);
			}
			service.saveTrack = function(trackDto) {
				return $http.post(CONSTANTS.saveTrack, trackDto);
			}
			return service;
		} ]);