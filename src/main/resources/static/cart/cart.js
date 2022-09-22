angular.module('store-front').controller('cartController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/app/api/v1/cart';

    $scope.loadCart = function () {
        $http.post(contextPath, $localStorage.cartName)
            .then(function (response) {
                $scope.productList = response.data;
            });

    };

    $scope.fromCart = function (id) {
        $http.post(contextPath + '/remove/' + id, $localStorage.cartName)
            .then(function (response) {
                console.log("тоавар удален из корзины");
                $scope.loadCart();
            });
    };

    $scope.clear = function () {
        $http.post(contextPath + '/clear', $localStorage.cartName)
            .then(function (response) {
                console.log("корзина очищена");
                $scope.loadCart();
            });
    };

    $scope.addToCart = function (id) {
        $http.post(contextPath + '/' + id, $localStorage.cartName)
            .then(function (response) {
                console.log("еще один товар добавлен в корзину");
                $scope.loadCart();
            });
    };
    $scope.AllFromCart = function (id) {
        $http.post(contextPath + '/removeAll/' + id, $localStorage.cartName)
            .then(function (response) {
                console.log("ленейка товаров удалена");
                $scope.loadCart();
            });
    };

    $scope.addAddress = function () {
        $http.post('http://localhost:8189/app/api/v1/order/addAddress', $scope.address)
            .then(function successCallback(response) {
                $scope.address.country = null;
                $scope.address.city = null;
                $scope.address.street = null;
                $scope.address.house = null;
                $scope.address.flat = null;
                $scope.getAddresses();
            });
    };

    $scope.checkAddressList = function () {
        if ($scope.addressList?.length > 0) {
            return true;
        } else {
            return false;
        }
    };

    $scope.createOrder = function () {
        $http.post('http://localhost:8189/app/api/v1/order/' + $localStorage.cartName, $scope.orderDetailsDto)
            .then(function (response) {
                $scope.loadCart();
                $scope.orderDetails = null
                $scope.getCartCount();
            });
    };

    if ($localStorage.springToken) {
        $scope.getAddresses();
    }
    $scope.loadCart();
});