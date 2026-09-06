$(document).ready(function () {
  var dataTable = null;

  // ==================== Load Palette Data ====================
  function loadPaletteData() {
    $('#loading').show();
    $('#palette_body').empty();

    $.ajax({
      url: 'getClientsPaletteData',
      dataType: 'json',
      success: function (data) {
        $('#loading').hide();

        if (dataTable !== null) {
          dataTable.destroy();
          $('#palette_body').empty();
        }

        var totalOut = 0,
          totalReturned = 0,
          totalBalance = 0;

        $.each(data, function (index, item) {
          var balance = item.balance;
          var balanceClass = balance > 0 ? 'balance-positive' : 'balance-zero';

          totalOut += item.total_out;
          totalReturned += item.total_returned;
          totalBalance += balance;

          var row =
            '<tr>' +
            '<td>' +
            (index + 1) +
            '</td>' +
            '<td>' +
            item.rc_name +
            '</td>' +
            '<td>' +
            item.numero_rc +
            '</td>' +
            '<td class="text-center">' +
            item.total_out +
            '</td>' +
            '<td class="text-center">' +
            item.total_returned +
            '</td>' +
            '<td class="text-center ' +
            balanceClass +
            '">' +
            balance +
            '</td>' +
            '<td class="text-center">' +
            '<button type="button" class="btn btn-sm btn-success btn_retour" ' +
            'data-rc="' +
            item.numero_rc +
            '" ' +
            'data-name="' +
            item.rc_name +
            '">' +
            '<i class="far fa-undo"></i> Retour' +
            '</button>' +
            '</td>' +
            '<td class="text-center">' +
            '<button type="button" class="btn btn-sm btn-info btn_historique" ' +
            'data-rc="' +
            item.numero_rc +
            '" ' +
            'data-name="' +
            item.rc_name +
            '">' +
            '<i class="far fa-history"></i> Historique' +
            '</button>' +
            '</td>' +
            '</tr>';

          $('#palette_body').append(row);
        });

        // Update footer totals
        $('#foot_out').text(totalOut);
        $('#foot_returned').text(totalReturned);
        $('#foot_balance').text(totalBalance);

        // Initialize DataTable
        var h = window.innerHeight;

        dataTable = $('#table_palette').DataTable({
          language: {
            url: 'resources/Plugins/datatable/lang/French.json',
          },
          ordering: true,
          bPaginate: false,
          scrollY: h - 350,
          scrollX: 'auto',
          deferRender: true,
        });
      },
      error: function (xhr, status, error) {
        $('#loading').hide();
        alert('Erreur lors du chargement des données palettes: ' + error);
      },
    });
  }

  // Initial load
  loadPaletteData();

  // ==================== Retour Button Click ====================
  $(document).on('click', '.btn_retour', function () {
    var rc = $(this).data('rc');
    var name = $(this).data('name');

    // Get the balance from the same row
    var balance = parseFloat($(this).closest('tr').find('td').eq(5).text());
    if (balance <= 0) {
      alert('Aucune palette à retourner pour ce client.');
      return;
    }

    $('#retour_rc').val(rc);
    $('#retour_name').val(name);
    $('#retour_client_display').text(name);
    $('#retour_rc_display').text(rc);
    $('#retour_quantity').val('');

    $('#modal_retour').modal('show');
  });

  // Auto-focus the quantity input when modal opens
  $('#modal_retour').on('shown.bs.modal', function () {
    $('#retour_quantity').focus();
  });

  // ==================== Submit Retour ====================
  $('#btn_submit_retour').click(function () {
    var rc = $('#retour_rc').val();
    var name = $('#retour_name').val();
    var quantity = parseFloat($('#retour_quantity').val());

    // Validation: must be a positive number
    if (isNaN(quantity) || quantity <= 0) {
      alert('Veuillez entrer une quantité valide (supérieure à 0).');
      $('#retour_quantity').focus();
      return;
    }

    // Disable submit button during request
    var $btn = $(this);
    $btn
      .prop('disabled', true)
      .html('<i class="far fa-spinner fa-spin"></i> En cours...');

    $.ajax({
      url: 'retourPalette',
      type: 'POST',
      data: {
        client_rc: rc,
        client_name: name,
        quantity: quantity,
      },
      success: function (response) {
        $btn
          .prop('disabled', false)
          .html('<i class="far fa-check"></i> Valider le Retour');

        // Close retour modal and show success
        $('#modal_retour').modal('hide');
        $('#modal_success').modal('show');

        // Reload table data
        loadPaletteData();
      },
      error: function (xhr, status, error) {
        $btn
          .prop('disabled', false)
          .html('<i class="far fa-check"></i> Valider le Retour');
        alert("Erreur lors de l'enregistrement du retour: " + error);
      },
    });
  });

  // Allow Enter key to submit retour form
  $('#retour_quantity').keypress(function (e) {
    if (e.which === 13) {
      e.preventDefault();
      $('#btn_submit_retour').click();
    }
  });

  // ==================== Historique Button Click ====================
  $(document).on('click', '.btn_historique', function () {
    var rc = $(this).data('rc');
    var name = $(this).data('name');

    $('#hist_client_display').text(name + ' — RC: ' + rc);
    $('#historique_body').empty();
    $('#hist_loading').show();

    $('#modal_historique').modal('show');

    $.ajax({
      url: 'getPaletteHistory',
      dataType: 'json',
      data: { numero_rc: rc },
      success: function (data) {
        $('#hist_loading').hide();
        $('#historique_body').empty();

        // Calculate running balance (process chronologically: oldest first)
        var sorted = data.slice().sort(function (a, b) {
          var dateA = a.date || '';
          var dateB = b.date || '';
          return dateA.localeCompare(dateB);
        });

        var runningBalance = 0;
        var balances = [];

        for (var i = 0; i < sorted.length; i++) {
          if (sorted[i].type === 'Sortie') {
            runningBalance += sorted[i].quantity;
          } else {
            runningBalance -= sorted[i].quantity;
          }
          sorted[i]._balance = runningBalance;
        }

        // Now display in reverse chronological order (most recent first)
        sorted.reverse();

        if (sorted.length === 0) {
          $('#historique_body').append(
            '<tr><td colspan="5" class="text-center text-muted">Aucun historique trouvé</td></tr>',
          );
          return;
        }

        $.each(sorted, function (index, item) {
          var typeBadge = '';
          if (item.type === 'Sortie') {
            typeBadge =
              '<span class="badge badge-sortie"><i class="far fa-arrow-up"></i> Sortie</span>';
          } else {
            typeBadge =
              '<span class="badge badge-retour"><i class="far fa-arrow-down"></i> Retour</span>';
          }

          var balanceClass =
            item._balance > 0 ? 'balance-positive' : 'balance-zero';

          var row =
            '<tr>' +
            '<td>' +
            (item.date || '-') +
            '</td>' +
            '<td class="text-center">' +
            typeBadge +
            '</td>' +
            '<td class="text-center">' +
            item.quantity +
            '</td>' +
            '<td>' +
            (item.reference || '-') +
            '</td>' +
            '<td class="text-center running-balance ' +
            balanceClass +
            '">' +
            item._balance +
            '</td>' +
            '</tr>';

          $('#historique_body').append(row);
        });
      },
      error: function (xhr, status, error) {
        $('#hist_loading').hide();
        alert("Erreur lors du chargement de l'historique: " + error);
      },
    });
  });
});
