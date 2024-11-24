$(document).ready(function () {
  $("#table-region").DataTable({
    ajax: {
      method: "GET",
      url: "api/region",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: (data, row, type, meta) => {
          return meta.row + 1;
        },
      },
      { data: "name" },
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
              onclick="deleteRegion(${data.id})"
            >
              Delete
            </button>
          </div>
        `;
        },
      },
    ],
  });
});

function getById(id) {
  $.ajax({
    method: "GET",
    url: "api/region/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: function (data) {
      $("#detail-id").text(data.id);
      $("#detail-name").text(data.name);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

$("#create-region").click((event) => {
  event.preventDefault();

  let valueName = $("#create-name").val();

  $.ajax({
    method: "POST",
    url: "api/region",
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify({
      name: valueName,
    }),
    success: (res) => {
      $("#create").modal("hide");
      $("#table-region").DataTable().ajax.reload();
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Your Region has been craeted...",
        showConfirmButton: false,
        timer: 2000,
      });
      $("#create-name").val("");
    },
    error: (err) => {
      if (err.status === 500) {
        Swal.fire({
          position: "center",
          icon: "warning",
          title: "Region already exists. Please use a different name.",
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
    url: "api/region/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: function (data) {
      $("#update-id").val(`${data.id}`);
      $("#up-detail-id").text(data.id);
      $("#update-name").val(data.name);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

$("#update-re").click((event) => {
  event.preventDefault();

  let valueId = $("#update-id").val();
  let valueName = $("#update-name").val();

  $.ajax({
    method: "PUT",
    url: "api/region/" + valueId,
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify({
      name: valueName,
    }),
    success: (res) => {
      $("#update").modal("hide");
      $("#table-region").DataTable().ajax.reload();
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Your Region has been updated...",
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

function deleteRegion(id) {
  $.ajax({
    method: "GET",
    url: "api/region/" + id,
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
            url: "api/region/" + id,
            beforeSend: addCSRFToken(),
            success: function (res) {
              Swal.fire(
                "Deleted! " + res.name + " has been deleted.",
                "success"
              );
              $("#table-region").DataTable().ajax.reload();
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
