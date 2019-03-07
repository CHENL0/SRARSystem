
var LoginApp = angular.module('LoginApp', []);
var MyApp = angular.module('MyApp', ['ngRoute','ngAnimate','ui.bootstrap','selectAddress','ngFileUpload']);
MyApp.config(function ($routeProvider) {

    $routeProvider

        // pjDetail page
        .when('/', {
            templateUrl: 'pjDetail.html',
            controller: 'pjDetailController'
        })

        // userProfile page
        .when('/userProfile', {
            templateUrl: 'userProfile.html',
            controller: 'userProfileController'
        })


        // tasks page
        .when('/tasks', {
                templateUrl: 'tasks.html',
                controller: 'taskController'
        })

    // tasks page
        .when('/submit', {
                templateUrl: 'submit.html',
                controller: 'submitController'
        })

        .when('/check', {
            templateUrl: 'check.html',
            controller: 'checkController'
        })

        .when('/apply', {
            templateUrl: 'applyFor.html',
            controller: 'applyController'
        })

        .when('/mission', {
            templateUrl: 'mission.html',
            controller: 'missionController'
        })

        .when('/audit', {
            templateUrl: 'audit.html',
            controller: 'auditController'
        })

        .when('/pjType', {
            templateUrl: 'pjType.html',
            controller: 'pjTypeController'
        })

        .when('/people', {
            templateUrl: 'people.html',
            controller: 'peopleController'
        })

        .when('/notify', {
            templateUrl: 'notifications.html',
            controller: 'notifyController'
        });
});
