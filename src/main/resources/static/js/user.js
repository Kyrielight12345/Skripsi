$(document).ready(function () {
  var role = document.getElementById("role").getAttribute("data-role");
  console.log(role);

  $("#tabel-user").DataTable({
    ajax: {
      method: "GET",
      url: "api/user",
      dataSrc: "",
    },
    columns: [
      {
        data: null,
        render: (data, row, type, meta) => {
          return meta.row + 1;
        },
      },
      { data: "username" },
      // {
      //   data: null,
      //   render: (data) => {
      //     console.log("Data row:", data);
      //     return data.relatedEntity && data.relatedEntity.name
      //       ? data.relatedEntity.name
      //       : "-";
      //   },
      // },
      { data: "role" },
      {
        data: null,
        render: (data) => {
          return `
            <div class="d-flex gap-3 justify-content-center">
                </button>
            <!-- Button trigger modal update -->
            <button
              type="button"
              class="btn btn-warning"
                class="btn btn-warning"
                 onclick="reset(${data.id})"
            >
              Reset Password
            </button>
            <!-- Button trigger modal delete -->
            <button
              type="button"
              class="btn btn-danger"
              onclick="deleteUser(${data.id})"
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

function deleteUser(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/user/" + id,
    success: function (data) {
      Swal.fire({
        title: "Apakah Yakin Ingin Menghapus ?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Ya",
      }).then((result) => {
        if (result.isConfirmed) {
          $.ajax({
            method: "Delete",
            url: window.location.origin + "/api/user/" + id,
            beforeSend: addCSRFToken(),
            success: function (res) {
              console.log(res);
              Swal.fire(" Data Berhasil Dihapus.");
              $("#tabel-user").DataTable().ajax.reload();
            },
            error: function (err) {
              console.log(err);
            },
          });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
          swal.fire({
            title: "batal",
            text: res.name + " Tidak terhapus",
            icon: "error",
          });
        }
      });
    },
  });
}

function reset(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/user/" + id,
    success: function (data) {
      Swal.fire({
        title: "Apakah Yakin Ingin Reset Password?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Ya",
      }).then((result) => {
        if (result.isConfirmed) {
          const updatedData = {
            password: "tpq123",
          };

          $.ajax({
            method: "PUT",
            url: window.location.origin + "/api/user/" + id,
            dataType: "JSON",
            contentType: "application/json",
            beforeSend: addCSRFToken(),
            data: JSON.stringify(updatedData),
            success: function (res) {
              console.log(res);
              Swal.fire("Password Berhasil Direset.");
              $("#tabel-user").DataTable().ajax.reload();
            },
            error: function (err) {
              console.log(err);
              Swal.fire(
                "Gagal Mengatur Ulang Password",
                err.responseText,
                "error"
              );
            },
          });
        } else if (result.dismiss === Swal.DismissReason.cancel) {
          Swal.fire({
            title: "Batal",
            text: "Reset password dibatalkan.",
            icon: "error",
          });
        }
      });
    },
    error: function (err) {
      console.log(err);
      Swal.fire(
        "Gagal Mengambil Data Pengguna",
        "Tidak dapat memuat data pengguna.",
        "error"
      );
    },
  });
}
