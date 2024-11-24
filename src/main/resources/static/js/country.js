$(document).ready(function () {
  $("#tabel-country").DataTable({
    ajax: {
      method: "GET",
      url: "api/country",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: (data, row, type, meta) => {
          return meta.row + 1;
        },
      },
      { data: "code" },
      { data: "name" },
      { data: "region.name" },
      {
        data: null,
        render: (data) => {
          return `
            <div class="d-flex gap-3 justify-content-center">
              <!-- Button trigger modal detail -->
              <button
                type="button"
                class="btn btn-primary"
                data-bs-toggle="modal"
                data-bs-target="#detail"
                onclick="getById(${data.id})"
              >
                Detail
              </button>
              <!-- Button trigger modal update -->
              <button
                type="button"
                class="btn btn-warning"
                data-bs-toggle="modal"
                data-bs-target="#update"
                onclick="beforeUpdate(${data.id})"
              >
                Update
              </button>
              <!-- Button trigger modal delete -->
              <button
                type="button"
                class="btn btn-danger"
                onclick="deleteCountry(${data.id})"
              >
                Delete
              </button>
            </div>
          `;
        },
      },
    ],
  });

  $("#btn-region").click((event) => {
    event.preventDefault();
    let selectElement = $("#region-id");
    selectElement.empty();
    let option = $("<option>").val("").text("Select Region");
    selectElement.append(option);
    $.ajax({
      method: "GET",
      url: "api/region",
      dataType: "json",
      success: function (data) {
        data.forEach(function (region) {
          let option = $("<option>").val(region.id).text(region.name);
          selectElement.append(option);
        });
      },
      error: (err) => {
        console.log(err);
      },
    });
  });
});

function getById(id) {
  $.ajax({
    method: "GET",
    url: "api/country/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: function (data) {
      $("#id").text(data.id);
      $("#code-country").text(data.code);
      $("#name-country").text(data.name);
      $("#name-region").text(data.region.name);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

$("#create-country").click((event) => {
  event.preventDefault();

  let valuecode = $("#code").val();
  let valueName = $("#create-name").val();
  let valueRegionId = $("#region-id").val();

  $.ajax({
    method: "POST",
    url: "api/country",
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify({
      code: valuecode,
      name: valueName,
      region: { id: valueRegionId },
    }),
    success: (res) => {
      $("#create").modal("hide");
      $("#tabel-country").DataTable().ajax.reload();
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Your country has been craeted...",
        showConfirmButton: false,
        timer: 2000,
      });
      $("#create-name").val("");
      $("#code").val("");
    },
    error: (err) => {
      if (err.status === 500) {
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "Code Country already exists. Please use a different code.",
          showConfirmButton: false,
          timer: 2000,
        });
      } else {
        console.log(err);
      }
    },
  });
});

function beforeUpdate(id) {
  $.ajax({
    method: "GET",
    url: "api/region",
    dataType: "json",
    success: function (data) {
      let selectElement = $("#region-country");
      selectElement.empty();
      data.forEach(function (region) {
        let option = $("<option>").val(region.id).text(region.name);
        selectElement.append(option);
      });
      $.ajax({
        method: "GET",
        url: "api/country/" + id,
        dataType: "JSON",
        contentType: "application/json",

        success: function (data) {
          $("#country-id").val(`${data.id}`);
          $("#country-code").val(data.code);
          $("#country-name1").val(data.name);
          $("#region-country").val(data.region.id);
        },
        error: (err) => {
          console.log(err);
        },
      });
    },
    error: (err) => {
      console.log(err);
    },
  });
}

$("#update-country").click((event) => {
  event.preventDefault();

  let valueId = $("#country-id").val();
  let valuecode = $("#country-code").val();
  let valueName = $("#country-name1").val();
  let valueRegionId = $("#region-country").val();

  $.ajax({
    method: "PUT",
    url: "api/country/" + valueId,
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify({
      code: valuecode,
      name: valueName,
      region: { id: valueRegionId },
    }),
    success: (res) => {
      $("#update").modal("hide");
      $("#tabel-country").DataTable().ajax.reload();
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Your Country has been updated...",
        showConfirmButton: false,
        timer: 2000,
      });
      $("#update-name").val("");
    },
    error: (err) => {
      console.log(err);
    },
  });
});

function deleteCountry(id) {
  $.ajax({
    method: "GET",
    url: "api/country/" + id,
    success: function (data) {
      Swal.fire({
        title: "Are you sure delete " + data.name + " ?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!",
      }).then((result) => {
        if (result.isConfirmed) {
          $.ajax({
            method: "Delete",
            url: "api/country/" + id,
            beforeSend: addCSRFToken(),
            success: function (res) {
              Swal.fire(
                "Deleted! " + res.name + " has been deleted.",
                "success"
              );
              $("#tabel-country").DataTable().ajax.reload();
            },
            error: function (err) {
              console.log(err);
            },
          });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
          swal.fire({
            title: "Cancelled",
            text: "Your imaginary data is safe :)",
            icon: "error",
          });
        }
      });
    },
  });
}
