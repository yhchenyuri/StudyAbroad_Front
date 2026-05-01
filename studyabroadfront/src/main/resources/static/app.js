var app = angular.module('myApp', []);

app.config(function($httpProvider) {
    // ⭐ 讓 session cookie 能帶過去
    $httpProvider.defaults.withCredentials = true;
});