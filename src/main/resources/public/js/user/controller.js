'use strict';

App.controller('UserController', ['$scope', 'UserService',
    function ($scope, UserService) {
        var self = this;
        self.user = {name: ''};

        self.loginUser = function (user) {
            UserService.loginUser(user);
        };

        self.submit = function () {
            self.loginUser(self.user);
        };

    }]);