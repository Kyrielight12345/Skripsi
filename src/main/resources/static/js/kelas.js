$(document).ready(function () {
  $("#tabel-kelas").DataTable({
    ajax: {
      method: "GET",
      url: "api/kelas",
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
                    <!-- Button trigger modal update -->
                    <button
                      type="button"
                      sec:authorize="hasRole('KEPALA')"
                      class="btn btn-warning"
                     onclick="navigateAndFetch(${data.id})"
                    >
                      Update
                    </button>
                    <!-- Button trigger modal delete -->
                    <button
                      type="button"
                      sec:authorize="hasRole('KEPALA')"
                      class="btn btn-danger"
                      onclick="deleteKelas(${data.id})"
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
$("#form-kelas").on("submit", (event) => {
  event.preventDefault();

  let kelas = {
    name: $("#create-kelas").val(),
  };
  console.log(kelas);

  $.ajax({
    method: "POST",
    url: window.location.origin + "/api/kelas/create",
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify(kelas),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data Kelas berhasil disimpan!",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        window.location.href = window.location.origin + "/kelas";
      });
    },
    error: (err) => {
      Swal.fire({
        position: "center",
        icon: "error",
        title: "Terjadi kesalahan!",
        showConfirmButton: false,
        timer: 2000,
      });
      console.log(err);
    },
  });
});

function deleteKelas(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/kelas/" + id,
    success: function (data) {
      Swal.fire({
        title: "Apakah Yakin Ingin Menghapus " + data.name + " ?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Ya",
      }).then((result) => {
        if (result.isConfirmed) {
          $.ajax({
            method: "Delete",
            url: window.location.origin + "/api/kelas/" + id,
            beforeSend: addCSRFToken(),
            success: function (res) {
              Swal.fire("Berhasil! " + res.name + " berhasil Dihapus.");
              $("#tabel-kelas").DataTable().ajax.reload();
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

function beforeUpdate(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/kelas/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: function (data) {
      console.log(data);
      $("#edit-id-kelas").val(`${data.id}`);
      $("#edit-nama-kelas").val(data.name);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

$("#edit-kelas").on("submit", (event) => {
  event.preventDefault();

  let valueId = $("#edit-id-kelas").val();
  let valueName = $("#edit-nama-kelas").val();

  $.ajax({
    method: "PUT",
    url: window.location.origin + "/api/kelas/" + valueId,
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify({
      name: valueName,
    }),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data Kelas berhasil diupdate!",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        window.location.href = window.location.origin + "/kelas";
      });
    },
    error: (err) => {
      console.log(err);
    },
  });
});

function navigateAndFetch(id) {
  window.location.href = `/kelas/update?id=${id}`;
}

document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const kelasId = urlParams.get("id");

  if (kelasId) {
    beforeUpdate(kelasId);
  } else {
    console.error("ID Kelas tidak ditemukan di URL");
  }
});
