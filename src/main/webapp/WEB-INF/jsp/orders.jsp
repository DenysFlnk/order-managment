<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="fragments/header.jsp"/>

<body onload="loadContent()">
<jsp:include page="fragments/body-header.jsp"/>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/orders.js" defer></script>
<br/>
<h2 class="text-center">Order list</h2>
<br/>
<div class="container-fluid">
    <button class="btn btn-success btn-lg btn-block" onclick="add()">
        <span class="fas fa-plus fa-lg"></span>
        Add order
    </button>
</div>
<br/>
<table class="table table-bordered table-inc-font" id="datatable">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Customer name</th>
        <th scope="col">Customer phone</th>
        <th scope="col">Customer email</th>
        <th scope="col">Prepaid, uah</th>
        <th scope="col">Surcharge, uah</th>
        <th scope="col">Summary cost, uah</th>
        <th scope="col" style="color: lightgreen">Creation date</th>
        <th scope="col" style="color: orange">Delivery date</th>
        <th scope="col">Notes</th>
        <th scope="col"><span class="fa fa-ellipsis"></span></th>
        <th scope="col"><span class="fa fa-ellipsis"></span></th>
    </tr>
    </thead>
    <tbody id="datatableBody">
    </tbody>
</table>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="modalTitle">Create order</h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <h5>Customer info:</h5>
                    <div class="form-group">
                        <label for="customerName" class="col-form-label">Name</label>
                        <input type="text" class="form-control" id="customerName" name="customerName"
                               placeholder="Example E.E.">
                    </div>

                    <div class="form-group">
                        <label for="customerPhone" class="col-form-label">Phone</label>
                        <input type="text" class="form-control" id="customerPhone" name="customerPhone"
                               placeholder="+38(***)***-**-**">
                    </div>

                    <div class="form-group">
                        <label for="customerEmail" class="col-form-label">Email</label>
                        <input type="email" class="form-control" id="customerEmail" name="customerEmail"
                               placeholder="example@example.com">
                    </div>
                    <h5>Order info:</h5>
                    <div class="form-group">
                        <label for="prepaid" class="col-form-label">Prepaid</label>
                        <input  type="number" class="form-control" id="prepaid" name="prepaid"
                               placeholder="0">
                    </div>

                    <div class="form-group">
                        <label for="creationDate" class="col-form-label">Creation date</label>
                        <input type="date" class="form-control" id="creationDate" name="creationDate"
                               placeholder="dd.mm.yyyy">
                    </div>

                    <div class="form-group">
                        <label for="deliveryDate" class="col-form-label">Delivery date</label>
                        <input type="date" class="form-control" id="deliveryDate" name="deliveryDate"
                               placeholder="dd.mm.yyyy">
                    </div>

                    <div class="form-group">
                        <label for="note" class="col-form-label">Note</label>
                        <input type="text" class="form-control" id="note" name="note"
                               placeholder="some notes...">
                    </div>
                    <input type="hidden" id="orderStatus" name="orderStatus" value="IN_WORK">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    Cancel
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    Save
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
