'use strict'

var module = angular.module('track.controllers', []);
module.controller("TrackController", [ "$scope", "TrackService",
		function($scope, TrackService) {

			$scope.track = {
				trackId : null,
				trackName : null,
				carDtos : []
			};
			$scope.cars = [];
			
			TrackService.getTrackById(1).then(function(value) {
				console.log(value.data);
			}, function(reason) {
				console.log("error occured");
			}, function(value) {
				console.log("no callback");
			});

			$scope.saveTrack= function() {
				$scope.track.cars = $scope.cars.map((car) => {
					return  {carId: null, carName: skill};
				});
				TrackService.saveTrack($scope.track).then(function() {
					console.log("cars");
					TrackService.getAllTracks().then(function(value) {
						$scope.allTracks= value.data;
					}, function(reason) {
						console.log("error occured");
					}, function(value) {
						console.log("no callback");
					});

					$scope.cars = [];
					$scope.track = {
						trackId : null,
						trackName : null,
						carDtos : []
					};
				}, function(reason) {
					console.log("error occured");
				}, function(value) {
					console.log("no callback");
				});
			}
		} ]);