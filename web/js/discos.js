var app = angular.module('discosApp', ['ngResource']);

var serviceURL = "//localhost:8080"

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
		    url: serviceURL + "/ProyectoDiscos/webresources/persistencia.artistas/search/:title"}
	    });
});

function AppController($scope, $location, discosFactory) {

    function cargarDiscos() {
	$scope.albumes = discosFactory.query();
    }

    $scope.agregar = function() {
	discosFactory.save($scope.nuevoDisco, cargarDiscos)
    };

    $scope.NuevoDisco = {id: 0, artista: {nombre: ""}, titulo: "", fecha: ""};
    $scope.DiscoModificado = $scope.NuevoDisco;

    $scope.beginNuevo = function() {
	$scope.NuevoDisco = {id: 0, artista: {nombre: ""}, titulo: "", fecha: ""};
    }

    $scope.agregar = function() {
	if ($scope.NuevoDisco.artista.nombre === "") {
	    $scope.NuevoDisco.artista.nombre = $('#newInputArtista').val();
	}
	discosFactory.save($scope.NuevoDisco, cargarDiscos);
	$scope.NuevoDisco = {id: 0, artista: {nombre: ""}, titulo: "", fecha: ""};
	$('#newInputArtista').val("");
	$('#nuevoModal').modal('hide')
    }

    $scope.modificar = function() {
	//modificarModal
	if ($scope.NuevoDisco.artista.nombre === "") {
	    $scope.DiscoModificado.artista.nombre = $('#modificarArtista').val();
	}
	discosFactory.update($scope.DiscoModificado, cargarDiscos);
	$scope.DiscoModificado = {id: 0, artista: {nombre: ""}, titulo: "", fecha: ""};
	$('#modificarArtista').val("");
	$('#modificarModal').modal('hide')
    }

    $scope.editar = function(disco) {
	$scope.DiscoModificado = disco;
	console.log(disco);
	$('#modificarModal').modal('show')
    }

    $scope.borrar = function(disco) {
	discosFactory.delete(disco, cargarDiscos);
    }

    cargarDiscos();

    $('#nuevoDIV .typeahead').typeahead(null, {
	name: 'newInputArtista',
	displayKey: 'nombre',
	source: taAlbumes.ttAdapter()
    })
	    .on('typeahead:selected', function(e, data) {
		$scope.NuevoDisco.artista = data;
	    });
    $("#newInputArtista")
	    .change(function() {
		delete $scope.NuevoDisco.artista.aid;
	    });

    $('#modificarDIV .typeahead').typeahead(null, {
	name: 'modificarArtista',
	displayKey: 'nombre',
	source: taAlbumes.ttAdapter()
    })
	    .on('typeahead:selected', function(e, data) {
		$scope.DiscoModificado.artista = data;
	    });

    $("#modificarArtista")
	    .change(function() {
		delete $scope.NuevoDisco.artista.aid;
	    });
}
;

var taAlbumes = new Bloodhound({
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    remote: serviceURL + '/ProyectoDiscos/webresources/persistencia.artistas/search/%QUERY'
});

taAlbumes.initialize();

