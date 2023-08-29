<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<jsp:include page="fragments/header.jsp"/>

<body onload="loadContent()">
<jsp:include page="fragments/body-header.jsp"/>
<script type="text/javascript" src="resources/js/common.js" defer></script>
<script type="text/javascript" src="resources/js/order-detail.js" defer></script>

<br/>
<h2 class="text-center" id="orderNumber"></h2>
<div class="container">
    <form class="grey-background" id="orderForm">
        <input type="hidden" id="id" name="id">
        <h5>Customer info:</h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="customerName">Name</label>
                <input type="text" class="form-control" id="customerName" name="customerName"
                       placeholder="Example E.E.">
            </div>
            <div class="form-group col-md-4">
                <label for="customerPhone">Phone</label>
                <input type="text" class="form-control" id="customerPhone" name="customerPhone"
                       placeholder="+38(***)***-**-**">
            </div>
            <div class="form-group col-md-4">
                <label for="customerEmail">Email</label>
                <input type="text" class="form-control" id="customerEmail" name="customerEmail"
                       placeholder="example@example.com">
            </div>
        </div>
        <h5>Order info:</h5>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="creationDate">Creation date</label>
                <input type="date" class="form-control" id="creationDate" name="creationDate">
            </div>
            <div class="form-group col-md-4">
                <label for="deliveryDate">Delivery date</label>
                <input type="date" class="form-control" id="deliveryDate" name="deliveryDate">
            </div>
            <div class="form-group col-md-3">
                <label for="orderStatus">Order status:</label>
                <select class="form-control" id="orderStatus" name="orderStatus">
                    <option value="IN_WORK">in work</option>
                    <option value="COMPLETED">completed</option>
                </select>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="prepaid">Prepaid</label>
                <input type="number" class="form-control" id="prepaid" name="prepaid" placeholder="0">
            </div>
            <div class="form-group col-md-4">
                <label for="surcharge">Surcharge</label>
                <input type="number" class="form-control" id="surcharge" name="surcharge" placeholder="0" readonly>
            </div>
            <div class="form-group col-md-4">
                <label for="summaryCost">Summary cost</label>
                <input type="number" class="form-control" id="summaryCost" name="summaryCost" placeholder="0" readonly>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-12">
                <label for="note">Note</label>
                <input type="text" class="form-control" id="note" name="note" placeholder="some notes...">
            </div>
        </div>

        <div class="modal-footer">
            <button type="button" class="btn btn-secondary btn-lg" onclick="location.href='orders'">
                <span class="fa fa-close"></span>
                Cancel
            </button>
            <button type="button" class="btn btn-success btn-lg" onclick="saveOrder()">
                <span class="fa fa-check"></span>
                Save
            </button>
        </div>
    </form>
    <div class="grey-background">
        <div class="modal-footer d-flex justify-content-start">
            <h5>Documents</h5>
            <button type="button" class="btn btn-success" onclick="openDocumentModal()">
                <span class="fa fa-plus"></span>
            </button>
        </div>

        <div class="form-row">
            <table class="table" id="docTable">
                <thead>
                <tr>
                    <th hidden="hidden" scope="col">Id</th>
                    <th scope="col">Language</th>
                    <th scope="col">Office rate</th>
                    <th scope="col">Signs</th>
                    <th scope="col">Notarization cost</th>
                    <th scope="col">Office cost</th>
                    <th scope="col">Translator name</th>
                    <th scope="col">Translator rate</th>
                    <th scope="col">Translator tax</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody id="docTableBody">
                </tbody>
            </table>
        </div>

        <div class="modal-footer d-flex justify-content-start">
            <h5>Apostilles</h5>
            <button type="button" class="btn btn-success" onclick="openModal('aposModal')">
                <span class="fa fa-plus"></span>
            </button>
        </div>
        <div class="form-row">
            <table class="table" id="aposTable">
                <thead>
                <tr>
                    <th hidden="hidden" scope="col">Id</th>
                    <th scope="col">Title</th>
                    <th scope="col">Submission country</th>
                    <th scope="col">Submission Department</th>
                    <th scope="col">Cost</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody id="aposTableBody">
                </tbody>
            </table>
        </div>
    </div>
</div>


<div class="modal fade" tabindex="-1" id="docModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Document</h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="docForm">
                    <input type="hidden" id="documentId" name="id">
                    <div class="form-group">
                        <label for="isHardComplexity" class="col-form-label">Hard complexity</label>
                        <input type="checkbox" id="isHardComplexity" name="isHardComplexity">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="documentLanguage" class="col-form-label">Language</label>
                            <select class="form-control" id="documentLanguage" name="language">
                                <option value="" disabled selected>Select a language</option>
                                <option>ALBANIAN</option>
                                <option>BASQUE</option>
                                <option>BULGARIAN</option>
                                <option>CATALAN</option>
                                <option>CROATIAN</option>
                                <option>CZECH</option>
                                <option>DANISH</option>
                                <option>DUTCH</option>
                                <option>ENGLISH</option>
                                <option>ESTONIAN</option>
                                <option>FINNISH</option>
                                <option>FRENCH</option>
                                <option>GERMAN</option>
                                <option>GREEK</option>
                                <option>HUNGARIAN</option>
                                <option>IRISH</option>
                                <option>ITALIAN</option>
                                <option>LATVIAN</option>
                                <option>LITHUANIAN</option>
                                <option>MALTESE</option>
                                <option>NORWEGIAN</option>
                                <option>POLISH</option>
                                <option>PORTUGUESE</option>
                                <option>ROMANIAN</option>
                                <option>RUSSIAN</option>
                                <option>SERBIAN</option>
                                <option>SLOVAK</option>
                                <option>SLOVENIAN</option>
                                <option>SPANISH</option>
                                <option>SWEDISH</option>
                                <option>TURKISH</option>
                                <option>UKRAINIAN</option>
                                <option>WELSH</option>
                                <option>CHINESE</option>
                                <option>JAPANESE</option>
                                <option>KOREAN</option>
                                <option>HINDI</option>
                                <option>ARABIC</option>
                                <option>URDU</option>
                                <option>BENGALI</option>
                            </select>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="officeRate" class="col-form-label">Office rate</label>
                            <input type="number" class="form-control" id="officeRate" name="officeRate"
                                   placeholder="0">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="signsNumber" class="col-form-label">Signs number, k</label>
                            <input type="number" class="form-control" id="signsNumber" name="signsNumber"
                                   placeholder="0">
                        </div>
                        <div class="form-group col-md-4">
                            <label for="notarizationCost" class="col-form-label">Notarization cost</label>
                            <input type="number" class="form-control" id="notarizationCost" name="notarizationCost"
                                   placeholder="0" value="0">
                        </div>
                        <div class="form-group col-md-4">
                            <label for="officeCost" class="col-form-label">Office cost</label>
                            <input type="number" class="form-control" id="officeCost" name="officeCost"
                                   placeholder="0">
                        </div>
                    </div>
                    <br/>
                    <br/>
                    <div class="form-group">
                        <h5>Translator
                            <button type="button" class="btn btn-warning" onclick="showTranslatorsFor()">
                                <span class="fa fa-ellipsis"></span>
                            </button>
                        </h5>
                        <input type="hidden" id="translatorId" name="translatorId">
                        <label for="translatorName" class="col-form-label">Name</label>
                        <input type="text" class="form-control" id="translatorName" name="translatorName" readonly>
                        <label for="translatorRate" class="col-form-label">Rate</label>
                        <input type="text" class="form-control" id="translatorRate" name="translatorRate">
                        <label for="translatorTax" class="col-form-label">Tax</label>
                        <input type="text" class="form-control" id="translatorTax" name="translatorTax">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    Cancel
                </button>
                <button type="button" class="btn btn-primary" onclick="saveDocument()">
                    <span class="fa fa-check"></span>
                    Save
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="aposModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Apostille</h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="aposForm">
                    <input type="hidden" id="apostilleId" name="id">
                    <div class="form-group">
                        <label for="title" class="col-form-label">Title</label>
                        <input type="text" class="form-control" id="title" name="title"
                               placeholder="'Title'">
                    </div>

                    <div class="form-group">
                        <label for="submissionCountry" class="col-form-label">Submission country</label>
                        <input type="text" class="form-control" id="submissionCountry" name="submissionCountry"
                               placeholder="Ukraine">
                    </div>

                    <div class="form-group">
                        <label for="submissionDepartment" class="col-form-label">Submission department</label>
                        <input type="email" class="form-control" id="submissionDepartment" name="submissionDepartment"
                               placeholder="Department">
                    </div>
                    <div class="form-group">
                        <label for="cost" class="col-form-label">Cost</label>
                        <input type="number" class="form-control" id="cost" name="cost"
                               placeholder="0">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    Cancel
                </button>
                <button type="button" class="btn btn-primary" onclick="saveApostille()">
                    <span class="fa fa-check"></span>
                    Save
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal" tabindex="-1" id="attachTranslatorDialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
        </div>
        <div class="modal-body">
            <h4 class="modal-title">Do you wish to attach a translator to document?</h4>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                <span class="fa fa-close"></span>
                No, do it later
            </button>
            <button type="button" class="btn btn-primary" data-dismiss="modal"
                    onclick="showTranslatorsFor()">
                <span class="fa fa-check"></span>
                Yes, show translators
            </button>
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="changeTranslator">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Translators</h4>
                <button type="button" class="close" data-dismiss="modal" onclick="closeNoty()">&times;</button>
            </div>
            <div class="modal-body">
                <table class="table" id="translatorTable">
                    <thead>
                    <tr>
                        <th hidden="hidden" scope="col">Id</th>
                        <th scope="col">Name</th>
                        <th scope="col">Email</th>
                        <th scope="col">Language</th>
                        <th scope="col">Rate</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody id="translatorTableBody">
                    </tbody>
                </table>
                <div class="d-flex justify-content-center">
                    <ul class="pagination" id="translatorPagination">
                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="closeNoty()">
                    <span class="fa fa-close"></span>
                    Cancel
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>