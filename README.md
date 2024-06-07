# BPMN Workflow Recruitment Management Project

## Introduction
This project aims to optimize the recruitment process in a professional environment using Business Process Model and Notation (BPMN) and workflow principles. It focuses on enhancing efficiency, communication, and candidate experience through detailed process modeling and automation.

## Project Context
In a competitive job market, efficient recruitment processes are vital for attracting and retaining top talent. By optimizing these processes, companies can reduce costs, shorten timelines, and improve hire quality and stakeholder satisfaction.

## Objectives
- Design an optimized and automated recruitment process incorporating industry best practices.
- Enhance collaboration and communication among recruitment stakeholders.
- Improve candidate experience and smoothen the onboarding process.

## Functional Requirements
- Job posting creation and publication.
- Automated candidate application management and profile filtering.
- Interview scheduling and coordination.
- Recruitment process tracking.

## Product Backlog
- Managers can submit recruitment requests to initiate the process.
- Conduct technical interviews to assess candidate skills.
- HR and Managers can make acceptance or rejection decisions post-interviews.
- HR evaluates recruitment requests for alignment with company needs.
- HR prepares and signs contracts with accepted candidates.
- HR sorts applications and invites shortlisted candidates for interviews.
- Candidates can view job listings, apply online, and track their application status.
- Candidates can negotiate contract terms once accepted.

## Acceptance Criteria
- Automated job postings on recruitment platforms.
- Automatic sending and receiving of applications.
- Automatic evaluation of applications based on predefined criteria.
- Systematic analysis of interview feedback.
- Automatic cancellation of job offers after a set period.
- Automatic hiring upon offer acceptance.

## Modeled Business Processes
The recruitment process is divided into key stages:
1. **Department Manager**: Identifies the need and submits a recruitment request.
2. **HR Department**: Validates requests, publishes job ads, receives and evaluates applications, organizes interviews, analyzes feedback, manages job offer cancellations, and finalizes hiring.
3. **Candidate**: Submits applications, participates in interviews, and accepts or declines offers.

## Technologies Used
- **Camunda**: For BPMN modeling, execution, and monitoring.
- **IntelliJ IDEA**: For Java client development.

## Java Clients
- **Job Poster candidacy sending**: Distributes job ads on various platforms and send candidacy to HR department.
- **Application Evaluator**: Analyzes applications based on preset criteria. In this model we are accepting the candidacy only if the number of years of experience is more than 2 years.
- **Feedback Receiver and Analyzer**: Manages interview feedback to continuously improve the recruitment process.
- **Decision Communicator**: Sends final decision of the condidate about contract to HR department.
- **Candidate hiring**: Hiring the condidate.

## Conclusion
The BPMN model offers a structured and clear representation of the recruitment process, facilitating efficient talent management and operational improvements for organizations.
