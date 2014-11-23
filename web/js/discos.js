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
app.factory("discosFactory", function($resource) {
    return $resource(serviceURL + "/ProyectoDiscos/webresources/persistencia.discos/:did",
	    null,
	    {
		'update': {method: 'PUT',
		    params: {did: '@did'},
		    url: serviceURL + "/ProyectoDiscos/webresources/persistencia.discos/:did"}
	    });
});
app.factory("artistasFactory", function($resource) {
    return $resource(serviceURL + "/ProyectoDiscos/webresources/persistencia.artistas/:aid",
	    null,
	    {
		'update': {method: 'PUT',
		    params: {aid: '@aid'},
		    url: serviceURL + "/ProyectoDiscos/webresources/persistencia.artistas/:aid"},
		'search': {method: 'GET',
		    params: {title: '@title'},
		    url: serviceURL + "/ProyectoDiscos/webresources/persistencia.artistas/search/:title",
		    isArray: true}
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

function AppController($scope, $http, discosFactory, artistasFactory, rolesFactory) {

    function cargarDiscos() {
	$scope.albumes = discosFactory.query();
    }

    $scope.ListaArtistas = artistasFactory.query();



    $scope.ROL = rolesFactory.get();
    $scope.Logout = function() {
	rolesFactory.logout();
	$scope.ROL = rolesFactory.get();
    };

    $scope.ActualizarArtistas = function(arg) {
	console.log(arg);
	var artista = arg;
	arg = artista.nombre;
	$http.get(serviceURL + '/ProyectoDiscos/webresources/persistencia.artistas/search/' + encodeURI(arg)).
		success(function(data, status, headers, config) {
		    $scope.ListaArtistas = data;
		    data.forEach(function(e, idx, data) {
			if (e.nombre === arg.nombre) {
			    $scope.NuevoDisco.artista.nombre = e.nombre;
			    $scope.NuevoDisco.artista.aid = e.aid;
			} else {
			    delete $scope.NuevoDisco.artista.aid;
			}
		    });
		}).
		error(function(data, status, headers, config) {
		    console.log(data);
		});
    }



    $scope.agregar = function() {
	discosFactory.save($scope.nuevoDisco, cargarDiscos);
    };
    $scope.NuevoDisco = {artista: {nombre: ""}, titulo: "", fecha: ""};
    $scope.beginNuevo = function() {
	$scope.NuevoDisco = {artista: {nombre: ""}, titulo: "", fecha: ""};
    };

    $scope.agregar = function() {
	delete $scope.NuevoDisco.did;
	discosFactory.save($scope.NuevoDisco, cargarDiscos);
	$scope.NuevoDisco = {artista: {nombre: ""}, titulo: "", fecha: ""};
	$('#nuevoModal').modal('hide');
    };

    $scope.modificar = function() {
	discosFactory.update($scope.NuevoDisco, cargarDiscos);
	$scope.NuevoDisco = {artista: {nombre: ""}, titulo: "", fecha: ""};
	$('#modificarModal').modal('hide');
    };

    $scope.editar = function(disco) {
	$scope.NuevoDisco.titulo = disco.titulo;
	$scope.NuevoDisco.artista.nombre = disco.artista.nombre;
	$scope.NuevoDisco.artista.aid = disco.artista.aid;
	$scope.NuevoDisco.fecha = disco.fecha;
	$scope.NuevoDisco.did = disco.did;
	$('#modificarModal').modal('show');
    };

    $scope.borrar = function(disco) {
	discosFactory.delete(disco, cargarDiscos);
    };

    cargarDiscos();
}
;