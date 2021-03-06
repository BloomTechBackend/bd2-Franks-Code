#!/bin/bash
aws cloudformation create-stack --region us-east-2 --stack-name caching-groups-table --template-body file://cloudformation/groups_table.yaml --capabilities CAPABILITY_IAM
aws cloudformation create-stack --region us-east-2 --stack-name caching-groupmemberships-table --template-body file://cloudformation/group_memberships_table.yaml --capabilities CAPABILITY_IAM
aws cloudformation create-stack --region us-east-2 --stack-name caching-groupmembershipaudits --template-body file://cloudformation/group_membership_audits_table.yaml --capabilities CAPABILITY_IAM
