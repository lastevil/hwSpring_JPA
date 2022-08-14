angular.module('app', []).controller('cartController', function ($scope, $http, $rootScope) {
    const contextPath = 'http://localhost:8189/app/api/v1/cart';

    $scope.loadCart = function () {
        $http.post(contextPath, localStorage.cartName)
            .then(function (response) {
                $scope.productList = response.data;
            });

    };

    $scope.fromCart = function (id) {
        $http.post(contextPath + '/remove/' + id, localStorage.cartName)
            .then(function (response) {
                console.warn("тоавар удален из корзины");
                $scope.loadCart();
            });
    };

    $scope.clear = function () {
        $http.post(contextPath + '/clear', localStorage.cartName)
            .then(function (response) {
                console.warn("корзина очищена");
                $scope.loadCart();
            });
    };

    $scope.addToCart = function (id) {
        $http.post(contextPath + '/' + id, localStorage.cartName)
            .then(function (response) {
                console.warn("еще один товар добавлен в корзину");
                $scope.loadCart();
            });
    };
    $scope.AllFromCart = function (id) {
        $http.post(contextPath + '/removeAll/' + id, localStorage.cartName)
            .then(function (response) {
            console.warn("ленейка товаров удалена");
            $scope.loadCart();
        });
    };

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/api/v1/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    localStorage.springWebUser = $scope.user.username;
                    localStorage.springToken = response.data.token;
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {

            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
        localStorage.cartName = "";
    };

    $scope.clearUser = function () {
        delete localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
        localStorage.cartName ="cart_" + (Math.random() * 100);
        $scope.loadCart();
    };

    $rootScope.isUserLoggedIn = function () {
        if (localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    if(!localStorage.cartName){
        localStorage.cartName = "cart_" + (Math.random() * 100);
    }
    if (localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + localStorage.springToken;
        localStorage.cartName = "cart_" + localStorage.springWebUser;
    }
    $scope.loadCart();
});