        $("#myModal").on('show.bs.modal', function(e) {
            var groupId = $(e.relatedTarget).data('group-id');

            var cols = $('#group-' + groupId + ' td');
            var id = $(cols[1]).text();
            var name = $(cols[2]).text();
            var screen_name = $(cols[3]).text();

            $('#ID1').val(id);
            $('#name').val(name );
            $('#screen_name ').val(screen_name);

        });

        $("#myModal").on('hidden.bs.modal', function() {
            var form = $(this).find('form');
            form[0].reset();
        });