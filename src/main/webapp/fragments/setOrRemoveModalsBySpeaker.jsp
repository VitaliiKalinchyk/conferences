<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>


<div class="modal fade" id="exampleModalDefault1" tabindex="-1" aria-labelledby="exampleModalDefault1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content rounded-4 shadow">
            <div class="modal-header border-bottom-0">
                <h1 class="modal-title fs-5 text-md-center"><fmt:message key="set.for.report"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body py-0">
                <p><fmt:message key="set.for.report.confirmation"/></p>
            </div>
            <div class="modal-footer flex-column border-top-0">
                <form method="POST" action="controller">
                    <input type="hidden" name="action" value="set-speaker-for-event-by-speaker">
                    <input type="hidden" name="event-id" value="${requestScope.event.id}">
                    <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="yes"/></button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="exampleModalDefault2" tabindex="-1" aria-labelledby="exampleModalDefault2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content rounded-4 shadow">
            <div class="modal-header border-bottom-0">
                <h1 class="modal-title fs-5 text-md-center"><fmt:message key="decline.report"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body py-0">
                <p><fmt:message key="decline.report.confirmation"/></p>
            </div>
            <div class="modal-footer flex-column border-top-0">
                <form method="POST" action="controller">
                    <input type="hidden" name="action" value="set-speaker-for-event-by-speaker">
                    <input type="hidden" name="event-id" value="${requestScope.event.id}">
                    <button type="submit" class="btn btn-dark mt-4 mb-4"><fmt:message key="yes"/></button>
                </form>
            </div>
        </div>
    </div>
</div>