var role = document.getElementById("role").getAttribute("data-role");
console.log(role);
$(document).ready(function () {
  $("#tabel-pengajar").DataTable({
    ajax: {
      method: "GET",
      url: "api/pengajar",
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
      { data: "alamat" },
      {
        data: null,
        render: (data, type, row) => {
          return `${row.tempat_lahir} / ${row.tanggal_lahir}`;
        },
      },
      { data: "kelas.name" },
      { data: "tanggal_bergabung" },
      {
        data: null,
        render: (data) => {
          if (role == "[ROLE_KEPALA]") {
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
          } else {
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
              </div>
            `;
          }
        },
      },
    ],
  });
});
