var main = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click', function() {
            _this.save();
        });

       $('#btn-update').on('click', function() {
           _this.update();
        });
    },
    save : function() {

        var data = {
            title   : $('#title').val(),
            author  : $('#author').val(),
            content : $('#content').val()
        };

        $.ajax({
            type : 'POST',
            url  : '/api/v1/posts',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function() {
            alert('등록 성공');
            window.location.href = '/';
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },


    update : function () {
       var id = $('#id').val();
       var data = {
                   title   : $('#title').val(),
                   content : $('#content').val()
               };

               $.ajax({
                   type : 'PUT',
                   url  : '/api/v1/posts/' + id,
                   dataType : 'json',
                   contentType : 'application/json; charset=utf-8',
                   data : JSON.stringify(data)
               }).done(function() {
                   alert('등록 성공');
                   window.location.href = '/';
               }).fail(function(error) {
                   alert(JSON.stringify(error));
               });
    }
};

main.init();