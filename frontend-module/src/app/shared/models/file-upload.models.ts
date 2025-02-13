export class AsyncTaskStatusModel {

    jobId = "unknown";
    status = "unknown";

    constructor(model: any) {
        this.jobId = model.jobId;
        this.status = model.status;
    }
}