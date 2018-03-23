app.factory("authService", function ($http, $location) {

    var TOKEN_KEY = 'jwtToken';

    var setToken = function (token) {
        if (localStorage.getItem(TOKEN_KEY)) {
            removeToken();
        }
        localStorage.setItem(TOKEN_KEY, token)
    }

    var removeToken = function () {
        localStorage.removeItem(TOKEN_KEY);
    }

    var getToken = function () {
        return localStorage.getItem(TOKEN_KEY);
    }

    var createAuthorizationTokenHeader = function () {
        var token = getToken();
        if (token) {
            return {
                "Authorization": "Bearer " + token,
                'Content-Type': 'application/json'
            };
        } else {
            return {
                'Content-Type': 'application/json'
            };
        }
    }

    return {
        setToken: setToken,
        removeToken: removeToken,
        createAuthorizationTokenHeader: createAuthorizationTokenHeader,
    }
});