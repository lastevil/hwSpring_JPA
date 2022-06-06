angular.module('app', []).controller('cartController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1/cart';

    $scope.loadCart = function () {
        $http.get(contextPath)
            .then(function (response) {
                $scope.productList = response.data;
                $scope.getSum()
            });

    };

    $scope.fromCart = function (productId) {
        $http.delete(contextPath + '/' + productId)
            .then(function (response) {
                $scope.loadCart();
            })
    };

    $scope.AllFromCart = function (productId) {
        $http.delete(contextPath + '/delAll/' + productId)
            .then(function (response) {
                $scope.loadCart();
            })
    };

    $scope.addToCart = function (productId) {
        $http.post(contextPath + '/' + productId)
            .then(function (response) {
                $scope.loadCart();
            })
    };

    $scope.getSum = function () {
        $http.get(contextPath + '/sum')
            .then(function (response) {
                $scope.cartSum = response.data;
            })
    };

    $scope.loadCart();
});