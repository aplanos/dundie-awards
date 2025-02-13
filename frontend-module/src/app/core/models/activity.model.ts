export interface ActivityModel {
    id: number;
    organizationId: number;
    startedAt: Date;
    type: ActivityType;
    status: ActivityStatus;
    context: { [key: string]: string };
    log: string;
    createdAt: Date;
    updatedAt: Date;
}

// Enum definitions for ActivityType and ActivityStatus
export enum ActivityType {
    GIVE_ORGANIZATION_DUNDIE_AWARDS = "GIVE_ORGANIZATION_DUNDIE_AWARDS",
    UNKNOWN = "UNKNOWN"
}

export enum ActivityStatus {
    PENDING = "PENDING",
    RUNNING = "RUNNING",
    PAUSED = "PAUSED",
    SUCCEEDED = "SUCCEEDED",
    FAILED = "FAILED",
    CANCELLED = "CANCELLED",
    UNKNOWN = "UNKNOWN"
}
