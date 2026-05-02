loginapp.service('AuthService', function($http) {

    var API_URL = "/api/auth";

    // 登入
    this.login = function(data) {
        return $http.post(API_URL + "/login", data, {
            withCredentials: true
        });
    };

    // 取得目前使用者
    this.getCurrentUser = function() {
        return $http.get(API_URL + "/me", {
            withCredentials: true
        });
    };

    // 登出
    this.logout = function() {
        return $http.post(API_URL + "/logout", {}, {
            withCredentials: true
        });
    };

});