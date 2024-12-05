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
                      data-bs-toggle="modal"
                      data-bs-target="#update"
                      onclick="beforeUpdate(${data.id})"
                    >
                      Update
                    </button>
                    <!-- Button trigger modal delete -->
                    <button
                      type="button"
                      sec:authorize="hasRole('KEPALA')"
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
      });
      $("#form-santri")[0].reset();
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
