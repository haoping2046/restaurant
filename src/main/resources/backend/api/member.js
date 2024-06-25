function getMemberList (params) {
  return $axios({
    url: '/employee/page',
    method: 'get',
    params
  })
}

// change state of employee
function enableOrDisableEmployee (params) {
  return $axios({
    url: '/employee',
    method: 'put',
    data: { ...params }
  })
}

// add an employee
function addEmployee (params) {
  return $axios({
    url: '/employee',
    method: 'post',
    data: { ...params }
  })
}

// edit and employee
function editEmployee (params) {
  return $axios({
    url: '/employee',
    method: 'put',
    data: { ...params }
  })
}

// need query emp by id when edit an employee
function queryEmployeeById (id) {
  return $axios({
    url: `/employee/${id}`,
    method: 'get'
  })
}