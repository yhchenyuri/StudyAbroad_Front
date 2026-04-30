app.service('AuthService', function($http) {

    var API_URL = "http://localhost:8080/api/auth";

    // 登入
    this.login = function(data) {
        return $http.post(API_URL + "/login", data);
    };

    // 取得目前使用者
    this.getCurrentUser = function() {
        return $http.get(API_URL + "/me");
    };

    // 登出
    this.logout = function() {
        return $http.post(API_URL + "/logout");
    };

});