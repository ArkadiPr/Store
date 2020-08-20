var app = angular.module('app', ['ngRoute','ui.bootstrap']);
var contextPath = 'http://localhost:8190/store'




app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'about-page.html',
            controller: 'aboutController'
        })
        .when('/books', {
            templateUrl: 'book-store.html',
            controller: 'booksController'
        })
});



