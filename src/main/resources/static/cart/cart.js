angular.module('store-front', []).controller('cartController', function ($scope, $http) {
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
    $scope.loadCart();
});