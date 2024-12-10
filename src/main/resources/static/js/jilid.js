$(document).ready(function () {
  $("#tabel-jilid").DataTable({
    ajax: {
      method: "GET",
      url: "api/jilid",
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
      { data: "kelas.name" },
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
                      onclick="deleteJilid(${data.id})"
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

$("#form-jilid").on("submit", (event) => {
  event.preventDefault();

  let jilid = {
    name: $("#create-jilid").val(),
    kelas: {
      id: $("#create-kelas").val(),
    },
  };
  console.log(jilid);

  $.ajax({
    method: "POST",
    url: window.location.origin + "/api/jilid/create",
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify(jilid),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data jilid berhasil disimpan!",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        window.location.href = window.location.origin + "/jilid";
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

function deleteJilid(id) {
  $.ajax({
    method: "GET",
    url: window.location.origin + "/api/jilid/" + id,
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
            url: window.location.origin + "/api/jilid/" + id,
            beforeSend: addCSRFToken(),
            success: function (res) {
              Swal.fire("Berhasil! " + res.name + " berhasil Dihapus.");
              $("#tabel-jilid").DataTable().ajax.reload();
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
    url: window.location.origin + "/api/jilid/" + id,
    dataType: "JSON",
    contentType: "application/json",
    success: function (data) {
      console.log(data);
      $("#edit-id-jilid").val(`${data.id}`);
      $("#edit-id-kelas").val(data.kelas.id);
      $("#edit-nama-jilid").val(data.name);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

$("#edit-jilid").on("submit", (event) => {
  event.preventDefault();

  let valueId = $("#edit-id-jilid").val();
  let valueName = $("#edit-nama-jilid").val();
  let valuekelas = $("#edit-id-kelas").val();

  $.ajax({
    method: "PUT",
    url: window.location.origin + "/api/jilid/" + valueId,
    dataType: "JSON",
    contentType: "application/json",
    beforeSend: addCSRFToken(),
    data: JSON.stringify({
      name: valueName,
      kelas: {
        id: valuekelas,
      },
    }),
    success: (res) => {
      Swal.fire({
        position: "center",
        icon: "success",
        title: "Data Jilid berhasil diupdate!",
        showConfirmButton: false,
        timer: 2000,
      }).then(() => {
        window.location.href = window.location.origin + "/jilid";
      });
    },
    error: (err) => {
      console.log(err);
    },
  });
});

function navigateAndFetch(id) {
  window.location.href = `/jilid/update?id=${id}`;
}

document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const jilidId = urlParams.get("id");

  if (jilidId) {
    beforeUpdate(jilidId);
  } else {
    console.error("ID Jilid tidak ditemukan di URL");
  }
});
