angular.module('store-front').controller('orderController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/orders/api/v1/order';
    $scope.getOrders = function () {
        $http.get(contextPath)
            .then(function (response) {
                $scope.ordersList = response.data;
            });
    };

    $scope.deleteOrder = function (id) {
        $http.delete(contextPath + '/' + id)
            .then(function (response) {
                $scope.getOrders();
            });
    };

    $scope.payOrder = function (id) {
        $http.get('http://localhost:5555/orders/api/v1/qiwi/create/' + id)
            .then(function (response) {
                $scope.billid = response.data.billid;
                params = {
                    payUrl: response.data.responseUrl,
                    customFields: {
                        themeCode: "Maksym-GeLno-j5g0"
                    }
                }
                QiwiCheckout.openInvoice(params)
                    .then(function (onFullField) {
                        $http.post('http://localhost:5555/orders/api/v1/qiwi/capture/' + $scope.billid)
                    })
                    .then(function (onRejection) {
                        $scope.getOrders();
                    });

            });
    };

    $scope.getOrderItems = function (id) {
        $http.get(contextPath + '/' + id)
            .then(function (response) {
                $scope.itemsList = response.data;
            });
    };

    $scope.getOrders();
});