// Safari reports success of list attribute, so doing ghetto detection instead
yepnope({
    test: (!Modernizr.input.list || (parseInt($.browser.version) > 400)),
    yep: [
	'https://raw2.github.com/CSS-Tricks/Relevant-Dropdowns/master/js/jquery.relevant-dropdown.js',
	'https://raw2.github.com/CSS-Tricks/Relevant-Dropdowns/master/js/load-fallbacks.js'
    ]
});


var app = angular.module('discosApp', ['ngResource', 'ui.bootstrap']);

var serviceURL = "//localhost:8080";
app.factory("usersFactory", function($resource) {
    return $resource(serviceURL + "/ProyectoDiscos/webresources/persistencia.users/:uid",
	    null,
	    {
		'update': {method: 'PUT',
		    params: {uid: '@uid'},
		    url: serviceURL + "/ProyectoDiscos/webresources/persistencia.users/:uid"}
	    });
});

app.factory("rolesFactory", function($resource) {
    return $resource(serviceURL + "/ProyectoDiscos/webresources/roles/:aid",
	    null,
	    {
		'logout': {method: 'POST',
		    url: serviceURL + "/ProyectoDiscos/logout"}
	    });
});

function AppController($scope, usersFactory, rolesFactory) {

    $scope.usuarios = usersFactory.query();

    function cargarUsuarios() {
	$scope.usuarios = usersFactory.query();
    }

    $scope.ROL = rolesFactory.get();
    $scope.Logout = function() {
	rolesFactory.logout();
	$scope.ROL = rolesFactory.get();
    };

    $scope.agregar = function() {
	usersFactory.save($scope.NuevoUsuario, cargarUsuarios);
    };
    $scope.NuevoUsuario = {userid: "", nombre: "", groupid: "", password: ""};
    $scope.beginNuevo = function() {
	$scope.NuevoUsuario = {userid: "", nombre: "", groupid: "", password: ""};
    };

    $scope.agregar = function() {
	delete $scope.NuevoUsuario.did;
	usersFactory.save($scope.NuevoUsuario, cargarUsuarios);
	$scope.NuevoUsuario = {userid: "", nombre: "", groupid: "", password: ""};
	$('#nuevoModal').modal('hide');
    };

    $scope.modificar = function() {
	usersFactory.update($scope.NuevoUsuario, cargarUsuarios);
	$scope.NuevoUsuario = {userid: "", nombre: "", groupid: "", password: ""};
	$('#modificarModal').modal('hide');
    };

    $scope.editar = function(usuario) {
	$scope.NuevoUsuario.userid = usuario.userid;
	$scope.NuevoUsuario.nombre = usuario.nombre;
	$scope.NuevoUsuario.groupid = usuario.groupid;
	$scope.NuevoUsuario.password = "";
	$('#modificarModal').modal('show');
    };

    $scope.borrar = function(user) {
	usersFactory.delete(user, cargarUsuarios);
    };
}
;